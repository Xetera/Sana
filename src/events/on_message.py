from ..client import client


@client.event
async def on_message(message):
	# We don't care about bots
	if message.author.bot:
		return

