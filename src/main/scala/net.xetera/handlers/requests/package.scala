package handlers.requests
import scala.util.parsing.json.JSONObject
import java.net.HttpURLConnection

import play.api.libs.ws.DefaultBodyReadables._

import com.sun.deploy.net.HttpResponse
import com.twitter.util.Future

//object RequestMaker {
//	def get(url: String): String = {
//		val request: HttpResponse[String] = Http(url).asString
//		println(request)
//		request.body
//	}
//
//	def post(_url: String, data: Map[String, Any]): Unit ={
//
//		val responseBody: Future[scala.xml.Elem] =
//
//		println(response.body)
//	}
//
//	def post(url: String, data: String): Unit = {
//		val response = Http(url).postData(data).asString
//		println(response.body)
//	}
//
//}
