from sqlalchemy import Column, ForeignKey, Integer, Text, BigInteger, Table, Boolean
from sqlalchemy.ext.declarative import declarative_base


Base = declarative_base()
metadata = Base.metadata


class Message(Base):
    __tablename__ = 'Message'

    uid = Column(Integer, primary_key=True, index=True)
    message_text = Column(Text)
    message_from = Column(Text)
    message_from_name = Column(Text)
    in_response_to = Column(Text)
    handled = Column(Integer, nullable=False)
    received_at = Column(BigInteger)
    responded_at = Column(BigInteger)
    participant_id = Column(Text)
    puid = Column(Integer)


    def __init__(self, message_text, message_from, message_from_name, in_response_to, handled, received_at, responded_at, participant_id, puid):
        self.message_text = message_text
        self.message_from = message_from
        self.message_from_name = message_from_name
        self.in_response_to = in_response_to
        self.handled = handled
        self.received_at = received_at
        self.responded_at = responded_at
        self.participant_id = participant_id
        self.puid = puid


class Participant(Base):
    __tablename__ = 'Participant'

    uid = Column(Integer, primary_key=True)
    participant_id = Column(Text)


class SurveyResult(Base):
    __tablename__ = 'SurveyResult'

    uid = Column(Integer, primary_key=True)
    availability = Column(Integer, nullable=False)
    unavailability = Column(Boolean, nullable=False)
    urgency = Column(Integer, nullable=False)
    friend_urgency = Column(Integer, nullable=False)
    message_id = Column(ForeignKey(u'Message.uid'), nullable=False, index=True)
    participant_id = Column(Text)
    puid = Column(Integer)


    def __init__(self, availability, urgency, message_id, participant_id, puid, unavailability, friend_urgency):
        self.availability = availability
        self.urgency = urgency
        self.message_id = message_id,
        self.participant_id = participant_id
        self.puid = puid

class AllMessage(Base):
    __tablename__ = 'AllMessage'

    uid = Column(Integer, primary_key=True)
    body = Column(Text)
    thread_id = Column(Integer, nullable=False)
    type = Column(Integer, nullable=False)
    received_at = Column(BigInteger)
    participant_id = Column(Text)

    def __init__(self, body, thread_id, type, received_at, participant_id):
        self.body = body
        self.thread_id = thread_id
        self.type = type
        self.received_at = received_at
        self.participant_id = participant_id

class Thread(Base):
    __tablename__ = 'Thread'

    uid = Column(Integer, primary_key=True)
    contact_name = Column(Text)
    address = Column(Text)
    participant_id = Column(Text)

    def __init__(self, uid, contact_name, address, participant_id):
        self.uid = uid
        self.contact_name = contact_name
        self.address = address
        self.participant_id = participant_id

class RelationalSurvey(Base):
    __tablename__ = 'RelationalSurvey'

    uid = Column(Integer, primary_key=True)
    intimacy1 = Column(Integer, nullable=False)
    intimacy2 = Column(Integer, nullable=False)
    intimacy3 = Column(Integer, nullable=False)
    power1 = Column(Integer, nullable=False)
    power2 = Column(Integer, nullable=False)
    power3 = Column(Integer, nullable=False)
    contact_name = Column(Text)
    participant_id = Column(Text)

from flask import Flask, abort, request, jsonify, Response, render_template
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
app.config.from_pyfile('config.py')
db = SQLAlchemy(app)   

@app.route('/message/', methods = ['POST'])
def create_message():
    if not db.session.query(Participant).filter(Participant.participant_id == request.json.get('participant_id')).first():
        return Response("{'status':'failure'}", status=403, mimetype='application/json')

    message = Message(request.json.get('message_text'), request.json.get('message_from'), request.json.get('message_from_name'), request.json.get('in_response_to'), request.json.get('handled'), request.json.get('received_at'),
            request.json.get('responded_at'), request.json.get('participant_id'), request.json.get('puid'))
    db.session.add(message)
    db.session.commit()
    return Response("{'status':'success'}", status=201, mimetype='application/json')

@app.route('/surveyresult/', methods = ['POST'])
def create_surveyresult():
    if not db.session.query(Participant).filter(Participant.participant_id == request.json.get('participant_id')).first():
        return Response("{'status':'failure'}", status=403, mimetype='application/json')

    surveyresult= SurveyResult(request.json.get('availability'), request.json.get('urgency'), request.json.get('message_id'), request.json.get('participant_id'), request.json.get('puid'), request.json.get('unavailability'), request.json.get('friend_urgency'))
    db.session.add(surveyresult)
    db.session.commit()
    return Response("{'status':'success'}", status=201, mimetype='application/json')

@app.route('/thread/', methods = ['POST'])
def create_thread():
    if not db.session.query(Participant).filter(Participant.participant_id == request.json.get('participant_id')).first():
        return Response("{'status':'failure'}", status=403, mimetype='application/json')

    thread= Thread(request.json.get('uid'), request.json.get("contact_name"), request.json.get("address"), request.json.get("participant_id"))
    db.session.add(thread)
    db.session.commit()
    return Response("{'status':'success'}", status=201, mimetype='application/json')

@app.route('/allmessage/', methods = ['POST'])
def create_allmessage():
    if not db.session.query(Participant).filter(Participant.participant_id == request.json.get('participant_id')).first():
        return Response("{'status':'failure'}", status=403, mimetype='application/json')

    allmessage= AllMessage(request.json.get("body"), request.json.get("thread_id"), request.json.get("type"), request.json.get("received_at"), request.json.get("participant_id"))
    db.session.add(allmessage)
    db.session.commit()
    return Response("{'status':'success'}", status=201, mimetype='application/json')

@app.route('/finalsurvey/<participant_id>', methods = ['GET'] )
def get_final_survey(participant_id):
    if not db.session.query(Participant).filter(Participant.participant_id == participant_id).first():
        return Response("Access with participant id.", status=401)

    contacts = [c[0] for c in db.session.query(Message.message_from_name).filter(Message.participant_id == participant_id).distinct()]
    return render_template('survey.html', contacts=contacts, participant_id=participant_id)

@app.route('/processform/<participant_id>', methods=['POST'])
def process_form(participant_id):
    if not db.session.query(Participant).filter(Participant.participant_id == participant_id).first():
        return Response("Access with participant id.", status=401)
    contacts = [c[0] for c in db.session.query(Message.message_from_name).filter(Message.participant_id == participant_id).distinct()]
    for contact in contacts:
        result = RelationalSurvey()
        result.contact_name = contact
        result.participant_id = participant_id
        result.intimacy1 = request.form.get("intimacy1- " + contact, None)
        result.intimacy2 = request.form.get("intimacy2- " + contact, None)
        result.intimacy3 = request.form.get("intimacy3- " + contact, None)
        result.power1 = request.form.get("power1- " + contact, None)
        result.power2 = request.form.get("power2- " + contact, None)
        result.power3 = request.form.get("power3- " + contact, None)
        db.session.add(result)
        db.session.commit()
    return Response("Thank you! You have completed the study. You will receive your compensation in your email soon. Please contact heston@u.northwestern.edu if you have any questions.", status=200, mimetype="text/html")





if __name__ == "__main__":
        app.run()
