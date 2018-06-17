# About:

This sample project demonstrates very basic communication between fragments and an activity. Specifically, passing arguments into a fragment using a bundle, and reading fragment state using a public method defined on the fragment. This design is demonstrated by swaping fragments that each contain a chronometer. When the fragments are replaced, state is passed between fragments via the activity to keep the chronometer in sync.

# Additional Methods:

This example of fragment/activity communication only demonstrates a single design. There are other designs worth understanding:

1. Defining a public callback interface in a fragment to be implemented by
   the activity. The fragment can then check if its parent activity
   implements the interface before invoking the callbacks.
2. Using an EventBus (e.g. GreenRobot).

# Resources:
- [Official Android documentation on fragment to fragment communication.](https://developer.android.com/training/basics/fragments/communicating "https://developer.android.com/training/basics/fragments/communicating")
