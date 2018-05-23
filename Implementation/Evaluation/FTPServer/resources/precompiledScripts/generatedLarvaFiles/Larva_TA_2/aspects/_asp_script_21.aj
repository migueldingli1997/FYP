package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;
import com.guichaguri.minimalftp.handler.ConnectionHandler;

import larva.*;
public aspect _asp_script_21 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script_21.initialize();
}
}
before ( ConnectionHandler cH) : (execution(* ConnectionHandler.stat(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){
ConnectionHandler _cH;
boolean authenticated;
_cH =cH ;
authenticated =true ;

_cls_script_21 _cls_inst = _cls_script_21._get_cls_script_21_inst( _cH);
_cls_inst.cH = cH;
_cls_script_21.authenticated = authenticated;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*evt_stat_cH*/, 12/*evt_anySymbol_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*evt_stat_cH*/, 12/*evt_anySymbol_cH*/);
}
}
before ( ConnectionHandler cH) : (execution(* ConnectionHandler.mode(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){
ConnectionHandler _cH;
boolean authenticated;
_cH =cH ;
authenticated =true ;

_cls_script_21 _cls_inst = _cls_script_21._get_cls_script_21_inst( _cH);
_cls_inst.cH = cH;
_cls_script_21.authenticated = authenticated;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*evt_mode_cH*/, 12/*evt_anySymbol_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*evt_mode_cH*/, 12/*evt_anySymbol_cH*/);
}
}
before ( ConnectionHandler cH) : (execution(* ConnectionHandler.pasv(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){
ConnectionHandler _cH;
boolean authenticated;
_cH =cH ;
authenticated =true ;

_cls_script_21 _cls_inst = _cls_script_21._get_cls_script_21_inst( _cH);
_cls_inst.cH = cH;
_cls_script_21.authenticated = authenticated;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 6/*evt_pasv_cH*/, 12/*evt_anySymbol_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 6/*evt_pasv_cH*/, 12/*evt_anySymbol_cH*/);
}
}
before ( ConnectionHandler cH) : (execution(* ConnectionHandler.port(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){
ConnectionHandler _cH;
boolean authenticated;
_cH =cH ;
authenticated =true ;

_cls_script_21 _cls_inst = _cls_script_21._get_cls_script_21_inst( _cH);
_cls_inst.cH = cH;
_cls_script_21.authenticated = authenticated;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 8/*evt_port_cH*/, 12/*evt_anySymbol_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 8/*evt_port_cH*/, 12/*evt_anySymbol_cH*/);
}
}
before ( Object outcomeObj,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_script_21.channel_TAsToMain))) && args(outcomeObj)) {

synchronized(_asp_script_20.lock){
ConnectionHandler _cH;
String state;
final Outcome outcome =(Outcome )outcomeObj ;
state =outcome .getState ();
_cH =(ConnectionHandler )outcome .getVar (0 );

_cls_script_21 _cls_inst = _cls_script_21._get_cls_script_21_inst( _cH);
_cls_inst.outcomeObj = outcomeObj;
_cls_script_21.state = state;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 13/*evt_channelTAsToMain*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 13/*evt_channelTAsToMain*/);
}
}
before ( ConnectionHandler cH) : (execution(* ConnectionHandler.syst(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){
ConnectionHandler _cH;
boolean authenticated;
_cH =cH ;
authenticated =true ;

_cls_script_21 _cls_inst = _cls_script_21._get_cls_script_21_inst( _cH);
_cls_inst.cH = cH;
_cls_script_21.authenticated = authenticated;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*evt_syst_cH*/, 12/*evt_anySymbol_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*evt_syst_cH*/, 12/*evt_anySymbol_cH*/);
}
}
after ( ConnectionHandler cH) returning (boolean authenticated) : (call(* ConnectionHandler.authenticate(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){
ConnectionHandler _cH;
_cH =cH ;

_cls_script_21 _cls_inst = _cls_script_21._get_cls_script_21_inst( _cH);
_cls_inst.authenticated = authenticated;
_cls_inst.cH = cH;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 10/*evt_authenticate_cH*/, 12/*evt_anySymbol_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 10/*evt_authenticate_cH*/, 12/*evt_anySymbol_cH*/);
}
}
}