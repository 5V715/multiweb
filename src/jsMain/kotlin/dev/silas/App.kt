package dev.silas

import kotlinext.js.jsObject
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.html.js.onClickFunction
import react.RProps
import react.child
import react.dom.h1
import react.dom.li
import react.dom.ul
import react.functionalComponent
import react.useEffect
import react.useState

private val scope = MainScope()

val App = functionalComponent<RProps> { _ ->
    val (messages, setMessages) = useState(emptyList<Message>())

    useEffect(dependencies = listOf()) {
        scope.launch {
            setMessages(getMessages())
        }
    }

    h1 {
        +"List of Messages"
    }

    ul {
        messages.sortedByDescending(Message::priority).forEach { item ->
            li {
                attrs.onClickFunction = {
                    scope.launch {
                        deleteMessage(item)
                        setMessages(getMessages())
                    }
                }
                key = item.toString()
                +"[${item.priority}] ${item.desc} "
            }
        }
    }

    child(
        InputComponent,
        props = jsObject {
            onSubmit = { input ->
                val message = Message(input.replace("!", ""), input.count { it == '!' })
                scope.launch {
                    addMessage(message)
                    setMessages(getMessages())
                }
            }
        }
    )

}