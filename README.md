### Event processing
[Event](https://dictionary.cambridge.org/dictionary/english/event) is anything that happens, event can have a type, 
for example mouse click event or incoming http request event, typed events allow us
to store different information about different events, for mouse click it seems 
to be useful to store mouse pointer coordinates, when for incoming http request we can store 
connection related information.

Event object can be matched by a special object, that implements 
[EventMatcher](src/main/java/io/github/vananos/evento/domain/matcher/EventMatcher.java) interface,
EventMatcher is a predicate that can tell us if given event meet some condition.

An Object that is responsible for executing action when Event happens implements 
[EventHandler](src/main/java/io/github/vananos/evento/domain/EventHandler.java).

Plenty of EventHandlers is a rule, it can be described using
[RuleBuilder](src/main/java/io/github/vananos/evento/domain/lang/RuleBuilder.java). 

Rule can contain another Rules.

There are two types of RuleBuilder available, GroupRuleBuilderImpl and ConcreteRuleBuilderImpl.
GroupRuleBuilderImpl can contain another rules, 
ConcreteRuleBuilderImpl can contain only EventHandlers (it's a leaf of AST). 

Execution order

Assume we have next rules description  
```shell script
rule1 {
    (handler1)
    rule2 {
        (handler2)
        condition1
    }
}
```

now if event that meets condition1 happens, 
then handler1 and handler2 will be executed.

In current implementation, once event matched it will not be matched with another
rules, therefore only (handler1, handle2) will be executed, 
but (handler1, handler3) will be never executed.
```shell script
rule1 {
    (handler1)
    rule2 {
        (handler2)
        condition1
    }
    rule3 {
        (handler3)
        condition1
    }
}
```


There are minimal interface implementations are available now.

TODO:
* fix generics
* nice Kotlin DSL
* EventHandlers can generate events
