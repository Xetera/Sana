from src.client import client
from src.events import *
from src.config.secret import secrets


if __name__ == '__main__':
	token = None

	try:
		token = secrets['token']
	except KeyError:
		print('Secrets file does not contain a token')
		quit(1)

	print('Attempting to log in to bot...')
	client.run(token)
