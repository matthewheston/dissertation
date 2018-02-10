import os
basedir = os.path.abspath(os.path.dirname(__file__))

SQLALCHEMY_DATABASE_URI = "mysql://root:mypassword@127.0.0.1:6603/dissertation"
SQLALCHEMY_MIGRATE_REPO = os.path.join(basedir, 'db_repository')
