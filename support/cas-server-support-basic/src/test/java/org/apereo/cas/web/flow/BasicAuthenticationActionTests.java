package org.apereo.cas.web.flow;

import org.apereo.cas.config.CasAuthenticationEventExecutionPlanTestConfiguration;
import org.apereo.cas.config.CasBasicAuthenticationConfiguration;
import org.apereo.cas.config.CasCookieAutoConfiguration;
import org.apereo.cas.config.CasCoreAuthenticationConfiguration;
import org.apereo.cas.config.CasCoreAuthenticationHandlersConfiguration;
import org.apereo.cas.config.CasCoreAuthenticationPolicyConfiguration;
import org.apereo.cas.config.CasCoreAuthenticationPrincipalConfiguration;
import org.apereo.cas.config.CasCoreAuthenticationSupportConfiguration;
import org.apereo.cas.config.CasCoreAutoConfiguration;
import org.apereo.cas.config.CasCoreLogoutAutoConfiguration;
import org.apereo.cas.config.CasCoreMultifactorAuthenticationConfiguration;
import org.apereo.cas.config.CasCoreNotificationsAutoConfiguration;
import org.apereo.cas.config.CasCoreServicesConfiguration;
import org.apereo.cas.config.CasCoreTicketCatalogConfiguration;
import org.apereo.cas.config.CasCoreTicketIdGeneratorsConfiguration;
import org.apereo.cas.config.CasCoreTicketsConfiguration;
import org.apereo.cas.config.CasCoreTicketsSerializationConfiguration;
import org.apereo.cas.config.CasCoreUtilAutoConfiguration;
import org.apereo.cas.config.CasCoreWebAutoConfiguration;
import org.apereo.cas.config.CasMultifactorAuthenticationWebflowAutoConfiguration;
import org.apereo.cas.config.CasPersonDirectoryTestConfiguration;
import org.apereo.cas.config.CasRegisteredServicesTestConfiguration;
import org.apereo.cas.config.CasWebApplicationServiceFactoryConfiguration;
import org.apereo.cas.config.CasWebflowAutoConfiguration;
import org.apereo.cas.services.RegisteredServiceTestUtils;
import org.apereo.cas.util.MockRequestContext;
import org.apereo.cas.util.http.HttpUtils;
import org.apereo.cas.web.support.WebUtils;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.webflow.execution.Action;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This is {@link BasicAuthenticationActionTests}.
 *
 * @author Misagh Moayyed
 * @since 6.2.0
 */
@SpringBootTest(classes = {
    CasBasicAuthenticationConfiguration.class,
    RefreshAutoConfiguration.class,
    WebMvcAutoConfiguration.class,
    CasCoreMultifactorAuthenticationConfiguration.class,
    CasMultifactorAuthenticationWebflowAutoConfiguration.class,
    CasCoreLogoutAutoConfiguration.class,
    CasWebflowAutoConfiguration.class,
    CasCoreNotificationsAutoConfiguration.class,
    CasCoreServicesConfiguration.class,
    CasCoreWebAutoConfiguration.class,
    CasCoreAutoConfiguration.class,
    CasCoreAuthenticationConfiguration.class,
    CasCoreTicketsConfiguration.class,
    CasRegisteredServicesTestConfiguration.class,
    CasCoreTicketCatalogConfiguration.class,
    CasCoreTicketsSerializationConfiguration.class,
    CasCoreTicketIdGeneratorsConfiguration.class,
    CasCookieAutoConfiguration.class,
    CasCoreAuthenticationPolicyConfiguration.class,
    CasCoreAuthenticationHandlersConfiguration.class,
    CasCoreAuthenticationPrincipalConfiguration.class,
    CasWebApplicationServiceFactoryConfiguration.class,
    CasCoreAuthenticationSupportConfiguration.class,
        CasCoreUtilAutoConfiguration.class,
    CasPersonDirectoryTestConfiguration.class,
    CasAuthenticationEventExecutionPlanTestConfiguration.class
})
@Tag("WebflowAuthenticationActions")
class BasicAuthenticationActionTests {
    @Autowired
    @Qualifier(CasWebflowConstants.ACTION_ID_BASIC_AUTHENTICATION)
    private Action basicAuthenticationAction;

    @Autowired
    private ConfigurableApplicationContext applicationContext;
    @Test
    void verifyOperation() throws Throwable {
        val context = MockRequestContext.create(applicationContext);
        HttpUtils.createBasicAuthHeaders("casuser", StringUtils.reverse("casuser")).forEach(context::addHeader);
        WebUtils.putServiceIntoFlowScope(context, RegisteredServiceTestUtils.getService("https://google.com"));
        assertEquals(CasWebflowConstants.TRANSITION_ID_SUCCESS, this.basicAuthenticationAction.execute(context).getId());
    }
}
