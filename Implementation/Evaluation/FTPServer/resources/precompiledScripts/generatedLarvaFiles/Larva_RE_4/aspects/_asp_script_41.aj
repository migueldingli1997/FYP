package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.RE.RegExpHolder;
import RE.lib.basic.Symbol;
import com.guichaguri.minimalftp.handler.ConnectionHandler;

import larva.*;
public aspect _asp_script_41 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script_41.initialize();
}
}
after ( ConnectionHandler cH) returning (boolean needs) : (call(* *.needsUsername(..)) && this(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_40.lock){
ConnectionHandler _cH;
_cH =cH ;

_cls_script_41 _cls_inst = _cls_script_41._get_cls_script_41_inst( _cH);
_cls_inst.needs = needs;
_cls_inst.cH = cH;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*evt_needUser_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*evt_needUser_cH*/);
}
}
before ( ConnectionHandler cH) : (execution(* ConnectionHandler.pass(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_40.lock){
ConnectionHandler _cH;
_cH =cH ;

_cls_script_41 _cls_inst = _cls_script_41._get_cls_script_41_inst( _cH);
_cls_inst.cH = cH;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 10/*evt_pass_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 10/*evt_pass_cH*/);
}
}
before ( ConnectionHandler cH) : (call(* ConnectionHandler.createDataSocket(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_40.lock){
ConnectionHandler _cH;
_cH =cH ;

_cls_script_41 _cls_inst = _cls_script_41._get_cls_script_41_inst( _cH);
_cls_inst.cH = cH;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 14/*evt_createDataSocket_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 14/*evt_createDataSocket_cH*/);
}
}
before ( ConnectionHandler cH) : (call(* ConnectionHandler.onConnected(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_40.lock){
ConnectionHandler _cH;
_cH =cH ;

_cls_script_41 _cls_inst = _cls_script_41._get_cls_script_41_inst( _cH);
_cls_inst.cH = cH;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*evt_onConnected_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*evt_onConnected_cH*/);
}
}
after ( ConnectionHandler cH) returning (boolean needs) : (call(* *.needsPassword(..)) && this(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_40.lock){
ConnectionHandler _cH;
_cH =cH ;

_cls_script_41 _cls_inst = _cls_script_41._get_cls_script_41_inst( _cH);
_cls_inst.needs = needs;
_cls_inst.cH = cH;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 8/*evt_needPass_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 8/*evt_needPass_cH*/);
}
}
before ( ConnectionHandler cH) : (execution(* ConnectionHandler.user(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_40.lock){
ConnectionHandler _cH;
_cH =cH ;

_cls_script_41 _cls_inst = _cls_script_41._get_cls_script_41_inst( _cH);
_cls_inst.cH = cH;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 6/*evt_user_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 6/*evt_user_cH*/);
}
}
after ( ConnectionHandler cH) returning (boolean authenticated) : (call(* ConnectionHandler.authenticate(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_40.lock){
ConnectionHandler _cH;
_cH =cH ;

_cls_script_41 _cls_inst = _cls_script_41._get_cls_script_41_inst( _cH);
_cls_inst.authenticated = authenticated;
_cls_inst.cH = cH;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 12/*evt_authenticate_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 12/*evt_authenticate_cH*/);
}
}
}