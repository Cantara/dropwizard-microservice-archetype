#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.hello;

import ${package}.hello.api.Planet;
import ${package}.hello.api.Saying;
import ${package}.domain.counter.CounterService;
import java.util.Optional;

import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.constretto.annotation.Configuration;
import org.constretto.annotation.Configure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import static net.logstash.logback.argument.StructuredArguments.*;

@Controller
@Path(${applicationName}Resource.PATH)
@Api(${applicationName}Resource.PATH)
@Produces({"application/json"})
public class ${applicationName}Resource {
    private final static Logger log = LoggerFactory.getLogger(${applicationName}Resource.class);
    public static final String PATH = "/hello";

    private final String template;
    private final String defaultName;

    private final CounterService counterService;

    @Configure
    public ${applicationName}Resource(@Configuration String template, @Configuration String defaultName, CounterService counterService) {
        this.template = template;
        this.defaultName = defaultName;
        this.counterService = counterService;
        log.debug("{} Initialized with {}, {}", ${applicationName}Resource.class.getSimpleName(), kv("template", template), kv("defaultName", defaultName));
    }

    @Timed
    @GET
    @ApiOperation("Endpoint that will respond with hello and use the name provided as request parameter if any")
    public Saying hello(@QueryParam("name") @ApiParam(defaultValue = "Mr. Smith") Optional<String> name) {
        log.trace("{} {} {}", v("method", HttpMethod.GET), v("path", ${applicationName}Resource.PATH), kv("name", name.orElse("null")));
        final String value = String.format(template, name.orElse(defaultName));
        log.trace("{}", kv("value", value));
        Saying saying = new Saying(counterService.next(), value);
        log.trace("{}", fields(saying));
        return saying;
    }

    @Timed
    @POST
    @ApiOperation("Post planetName and yourName and be greeted.")
    @Consumes({"application/json"})
    public Saying hello(Planet planet) {
        log.trace("{} {} {}", v("method", HttpMethod.POST), v("path", ${applicationName}Resource.PATH), fields(planet));
        final String value = "Hello " + planet.getYourName() + " on planet " + planet.getPlanetName();
        log.trace("{}", kv("value", value));
        Saying saying = new Saying(counterService.next(), value);
        log.trace("{}", fields(saying));
        return saying;
    }
}
