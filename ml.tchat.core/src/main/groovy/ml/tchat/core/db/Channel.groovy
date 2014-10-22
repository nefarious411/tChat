package ml.tchat.core.db

@Labeled('channel')
class Channel extends GraphEntity {
  @Index String name
}
