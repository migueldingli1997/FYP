package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;
import com.guichaguri.minimalftp.handler.ConnectionHandler;
import com.guichaguri.minimalftp.FTPConnection;

import larva.*;
public aspect _asp_script_31 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script_31.initialize();
}
}
after ( ConnectionHandler cH) returning (boolean authenticated) : (call(* ConnectionHandler.authenticate(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_30.lock){
ConnectionHandler _cH;
_cH =cH ;

_cls_script_31 _cls_inst = _cls_script_31._get_cls_script_31_inst( _cH);
_cls_inst.authenticated = authenticated;
_cls_inst.cH = cH;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*evt_auth_cH*/, 12/*evt_anySymbol_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*evt_auth_cH*/, 12/*evt_anySymbol_cH*/);
}
}
before ( ConnectionHandler cH) : (call(* ConnectionHandler.createDataSocket(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_30.lock){
ConnectionHandler _cH;
boolean authenticated;
_cH =cH ;
authenticated =true ;

_cls_script_31 _cls_inst = _cls_script_31._get_cls_script_31_inst( _cH);
_cls_inst.cH = cH;
_cls_script_31.authenticated = authenticated;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*evt_cds_cH*/, 12/*evt_anySymbol_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*evt_cds_cH*/, 12/*evt_anySymbol_cH*/);
}
}
before ( ConnectionHandler cH) : (call(* ConnectionHandler.onDisconnected(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_30.lock){
ConnectionHandler _cH;
boolean authenticated;
_cH =cH ;
authenticated =true ;

_cls_script_31 _cls_inst = _cls_script_31._get_cls_script_31_inst( _cH);
_cls_inst.cH = cH;
_cls_script_31.authenticated = authenticated;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 10/*evt_onDisconnected_cH*/, 12/*evt_anySymbol_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 10/*evt_onDisconnected_cH*/, 12/*evt_anySymbol_cH*/);
}
}
before ( ConnectionHandler cH) : (execution(* ConnectionHandler.pass(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_30.lock){
ConnectionHandler _cH;
boolean authenticated;
_cH =cH ;
authenticated =true ;

_cls_script_31 _cls_inst = _cls_script_31._get_cls_script_31_inst( _cH);
_cls_inst.cH = cH;
_cls_script_31.authenticated = authenticated;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 8/*evt_pass_cH*/, 12/*evt_anySymbol_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 8/*evt_pass_cH*/, 12/*evt_anySymbol_cH*/);
}
}
before ( Object outcomeObj,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_script_31.channel_TAsToMain))) && args(outcomeObj)) {

synchronized(_asp_script_30.lock){
ConnectionHandler _cH;
String state;
final Outcome outcome =(Outcome )outcomeObj ;
state =outcome .getState ();
_cH =(ConnectionHandler )outcome .getVar (0 );

_cls_script_31 _cls_inst = _cls_script_31._get_cls_script_31_inst( _cH);
_cls_inst.outcomeObj = outcomeObj;
_cls_script_31.state = state;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 13/*evt_channelTAsToMain*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 13/*evt_channelTAsToMain*/);
}
}
before ( ConnectionHandler cH) : (call(* ConnectionHandler.onConnected(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_30.lock){
ConnectionHandler _cH;
boolean authenticated;
_cH =cH ;
authenticated =true ;

_cls_script_31 _cls_inst = _cls_script_31._get_cls_script_31_inst( _cH);
_cls_inst.cH = cH;
_cls_script_31.authenticated = authenticated;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*evt_onConnected_cH*/, 12/*evt_anySymbol_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*evt_onConnected_cH*/, 12/*evt_anySymbol_cH*/);
}
}
before ( ConnectionHandler cH,String username) : (execution(* ConnectionHandler.user(..)) && target(cH) && args(username) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*)) && if (!username .startsWith ("anon"))) {

synchronized(_asp_script_30.lock){
ConnectionHandler _cH;
boolean authenticated;
_cH =cH ;
authenticated =true ;

_cls_script_31 _cls_inst = _cls_script_31._get_cls_script_31_inst( _cH);
_cls_inst.cH = cH;
_cls_inst.username = username;
_cls_script_31.authenticated = authenticated;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 6/*evt_user_cH*/, 12/*evt_anySymbol_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 6/*evt_user_cH*/, 12/*evt_anySymbol_cH*/);
}
}
}