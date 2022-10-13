package info.pmarquezh.junjo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JunjoKotlinApplication

fun main(args: Array<String>) {
	runApplication<JunjoKotlinApplication>(*args)
}
