from sqlalchemy import Column, ForeignKey, Integer, Text, BigInteger
from sqlalchemy.orm import relationship
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
    urgency = Column(Integer, nullable=False)
    message_id = Column(ForeignKey(u'Message.uid'), nullable=False, index=True)
    participant_id = Column(Text)
    puid = Column(Integer)


    def __init__(self, availability, urgency, message_id, participant_id, puid):
        self.availability = availability
        self.urgency = urgency
        self.message_id = message_id,
        self.participant_id = participant_id
        self.puid = puid

from flask import Flask, abort, request, jsonify, Response
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
app.config.from_pyfile('config.py')
db = SQLAlchemy(app)   

@app.route('/message/', methods = ['POST'])
def create_message():
    message = Message(request.json.get('message_text'), request.json.get('message_from'), request.json.get('message_from_name'), request.json.get('in_response_to'), request.json.get('handled'), request.json.get('received_at'),
            request.json.get('responded_at'), request.json.get('participant_id'), request.json.get('puid'))
    db.session.add(message)
    db.session.commit()
    return Response("{'status':'success'}", status=201, mimetype='application/json')

@app.route('/surveyresult/', methods = ['POST'])
def create_surveyresult():
    surveyresult= SurveyResult(request.json.get('availability'), request.json.get('urgency'), request.json.get('message_id'), request.json.get('participant_id'), request.json.get('puid'))
    db.session.add(surveyresult)
    db.session.commit()
    return Response("{'status':'success'}", status=201, mimetype='application/json')

if __name__ == "__main__":
        app.run()
