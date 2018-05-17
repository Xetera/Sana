import listeners.EventListener
import net.dv8tion.jda.core.{AccountType, JDABuilder}
import settings.BotSettings

object Bot extends App {
	new JDABuilder(AccountType.BOT)
		.setToken(BotSettings.token)
		.addEventListener(EventListener)
		.buildAsync()
}
