package ml.tchat.core;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ActionEvent;
import org.pircbotx.hooks.events.ChannelInfoEvent;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.DisconnectEvent;
import org.pircbotx.hooks.events.FingerEvent;
import org.pircbotx.hooks.events.HalfOpEvent;
import org.pircbotx.hooks.events.IncomingChatRequestEvent;
import org.pircbotx.hooks.events.IncomingFileTransferEvent;
import org.pircbotx.hooks.events.InviteEvent;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.KickEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.ModeEvent;
import org.pircbotx.hooks.events.MotdEvent;
import org.pircbotx.hooks.events.NickAlreadyInUseEvent;
import org.pircbotx.hooks.events.NickChangeEvent;
import org.pircbotx.hooks.events.NoticeEvent;
import org.pircbotx.hooks.events.OpEvent;
import org.pircbotx.hooks.events.OwnerEvent;
import org.pircbotx.hooks.events.PartEvent;
import org.pircbotx.hooks.events.PingEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.events.QuitEvent;
import org.pircbotx.hooks.events.RemoveChannelBanEvent;
import org.pircbotx.hooks.events.RemoveChannelKeyEvent;
import org.pircbotx.hooks.events.RemoveChannelLimitEvent;
import org.pircbotx.hooks.events.RemoveInviteOnlyEvent;
import org.pircbotx.hooks.events.RemoveModeratedEvent;
import org.pircbotx.hooks.events.RemoveNoExternalMessagesEvent;
import org.pircbotx.hooks.events.RemovePrivateEvent;
import org.pircbotx.hooks.events.RemoveSecretEvent;
import org.pircbotx.hooks.events.RemoveTopicProtectionEvent;
import org.pircbotx.hooks.events.ServerPingEvent;
import org.pircbotx.hooks.events.ServerResponseEvent;
import org.pircbotx.hooks.events.SetChannelBanEvent;
import org.pircbotx.hooks.events.SetChannelKeyEvent;
import org.pircbotx.hooks.events.SetChannelLimitEvent;
import org.pircbotx.hooks.events.SetInviteOnlyEvent;
import org.pircbotx.hooks.events.SetModeratedEvent;
import org.pircbotx.hooks.events.SetNoExternalMessagesEvent;
import org.pircbotx.hooks.events.SetPrivateEvent;
import org.pircbotx.hooks.events.SetSecretEvent;
import org.pircbotx.hooks.events.SetTopicProtectionEvent;
import org.pircbotx.hooks.events.SocketConnectEvent;
import org.pircbotx.hooks.events.SuperOpEvent;
import org.pircbotx.hooks.events.TimeEvent;
import org.pircbotx.hooks.events.TopicEvent;
import org.pircbotx.hooks.events.UnknownEvent;
import org.pircbotx.hooks.events.UserListEvent;
import org.pircbotx.hooks.events.UserModeEvent;
import org.pircbotx.hooks.events.VersionEvent;
import org.pircbotx.hooks.events.VoiceEvent;
import org.pircbotx.hooks.events.WhoisEvent;
import org.pircbotx.hooks.types.GenericCTCPEvent;
import org.pircbotx.hooks.types.GenericChannelEvent;
import org.pircbotx.hooks.types.GenericChannelModeEvent;
import org.pircbotx.hooks.types.GenericChannelUserEvent;
import org.pircbotx.hooks.types.GenericDCCEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.pircbotx.hooks.types.GenericUserEvent;
import org.pircbotx.hooks.types.GenericUserModeEvent;

/**
 *
 */
public class TwitchListener extends ListenerAdapter<PircBotX> {

  @Override
  public void onAction(ActionEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onChannelInfo(ChannelInfoEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onConnect(ConnectEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onDisconnect(DisconnectEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onFinger(FingerEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onHalfOp(HalfOpEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onIncomingChatRequest(IncomingChatRequestEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onIncomingFileTransfer(IncomingFileTransferEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onInvite(InviteEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onJoin(JoinEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onKick(KickEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onMessage(MessageEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onMode(ModeEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onMotd(MotdEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onNickAlreadyInUse(NickAlreadyInUseEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onNickChange(NickChangeEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onNotice(NoticeEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onOp(OpEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onOwner(OwnerEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onPart(PartEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onPing(PingEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onPrivateMessage(PrivateMessageEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onQuit(QuitEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onRemoveChannelBan(RemoveChannelBanEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onRemoveChannelKey(RemoveChannelKeyEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onRemoveChannelLimit(RemoveChannelLimitEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onRemoveInviteOnly(RemoveInviteOnlyEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onRemoveModerated(RemoveModeratedEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onRemoveNoExternalMessages(RemoveNoExternalMessagesEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onRemovePrivate(RemovePrivateEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onRemoveSecret(RemoveSecretEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onRemoveTopicProtection(RemoveTopicProtectionEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onServerPing(ServerPingEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onServerResponse(ServerResponseEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onSetChannelBan(SetChannelBanEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onSetChannelKey(SetChannelKeyEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onSetChannelLimit(SetChannelLimitEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onSetInviteOnly(SetInviteOnlyEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onSetModerated(SetModeratedEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onSetNoExternalMessages(SetNoExternalMessagesEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onSetPrivate(SetPrivateEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onSetSecret(SetSecretEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onSetTopicProtection(SetTopicProtectionEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onSocketConnect(SocketConnectEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onSuperOp(SuperOpEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onTime(TimeEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onTopic(TopicEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onUnknown(UnknownEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onUserList(UserListEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onUserMode(UserModeEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onVersion(VersionEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onVoice(VoiceEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onWhois(WhoisEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onGenericCTCP(GenericCTCPEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onGenericUserMode(GenericUserModeEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onGenericChannelMode(GenericChannelModeEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onGenericDCC(GenericDCCEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onGenericMessage(GenericMessageEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onGenericChannel(GenericChannelEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onGenericUser(GenericUserEvent<PircBotX> event) throws Exception {

  }

  @Override
  public void onGenericChannelUser(GenericChannelUserEvent<PircBotX> event) throws Exception {

  }
}
