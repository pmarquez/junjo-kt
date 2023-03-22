package info.pmarquezh.junjo.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.*

/**
 * I18nConfig.kt<br></br><br></br>
 * Creation Date 2022-08-18 12:02<br></br><br></br>
 * **DESCRIPTION:**<br></br><br></br>
 *
 *
 *
 * <PRE>
 * <table width="90%" border="1" cellpadding="3" cellspacing="2">
 * <tr><th colspan="2">   History   </th></tr>
 *
 * <tr>
 * <td width="20%">Version 1.0<br></br>
 * Version Date: 2022-08-18 12:02<br></br>
 *
 * @author pmarquezh </td>
 * <td width="80%">
 *
 *Creation</td>
</tr> *
</table> *
</PRE> *
 * @author pmarquezh
 * @version 1.0 - 2022-08-18 12:02
 */
@Configuration
class I18nConfig: WebMvcConfigurer {
    @Value("\${info.pmarquezh.junjo.defaultLocaleLanguage}")
    private var lang: String? = "es"

    @Value("\${info.pmarquezh.junjo.defaultLocaleCountry}")
    private var country: String? = "ES"

    @Bean ( "messageSource" )
    fun messageSource ( ): MessageSource {
        val messageSource = ResourceBundleMessageSource ( )
        messageSource.setBasenames ( "language/messages" )
        messageSource.setDefaultEncoding ( "UTF-8" )
        return messageSource
    }

    @Bean
    fun localeResolver(): LocaleResolver {
        val slr = SessionLocaleResolver()
        slr.setDefaultLocale(Locale(lang, country))
        slr.setLocaleAttributeName("current.locale")
        slr.setTimeZoneAttributeName("current.timezone")
        return slr
    }

    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor {
        val localeChangeInterceptor = LocaleChangeInterceptor()
        localeChangeInterceptor.paramName = "language"
        return localeChangeInterceptor
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(localeChangeInterceptor())
    }

    @Bean
    override fun getValidator(): LocalValidatorFactoryBean? {
        val bean = LocalValidatorFactoryBean()
        bean.setValidationMessageSource(messageSource())
        return bean
    }
}