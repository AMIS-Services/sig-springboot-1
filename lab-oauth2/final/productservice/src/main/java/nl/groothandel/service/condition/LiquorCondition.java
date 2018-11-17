package nl.groothandel.service.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LiquorCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return "Liquor".equalsIgnoreCase(context.getEnvironment().getProperty("repository.name"));
    }
}
