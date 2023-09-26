package learn.ghar.androidapps.domain

import learn.ghar.androidapps.db.Subscriber

fun Subscriber.toSubscriberUI(): SubscriberUI {
    return SubscriberUI(
        id = id,
        name = name,
        email = email
    )
}

fun SubscriberUI.toSubscriber(): Subscriber {
    return Subscriber(
        id = id,
        name = name,
        email = email
    )
}