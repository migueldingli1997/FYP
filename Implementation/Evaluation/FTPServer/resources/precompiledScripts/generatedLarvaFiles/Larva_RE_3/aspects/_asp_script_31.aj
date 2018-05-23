package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.RE.RegExpHolder;
import RE.lib.basic.Symbol;
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
_cls_inst._call(thisJoinPoint.getSignature().toString(), 6/*evt_auth_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 6/*evt_auth_cH*/);
}
}
before ( ConnectionHandler cH) : (call(* ConnectionHandler.createDataSocket(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_30.lock){
ConnectionHandler _cH;
_cH =cH ;

_cls_script_31 _cls_inst = _cls_script_31._get_cls_script_31_inst( _cH);
_cls_inst.cH = cH;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 12/*evt_cds_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 12/*evt_cds_cH*/);
}
}
before ( ConnectionHandler cH) : (execution(* ConnectionHandler.pass(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_30.lock){
ConnectionHandler _cH;
_cH =cH ;

_cls_script_31 _cls_inst = _cls_script_31._get_cls_script_31_inst( _cH);
_cls_inst.cH = cH;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 10/*evt_pass_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 10/*evt_pass_cH*/);
}
}
before ( ConnectionHandler cH) : (call(* ConnectionHandler.onConnected(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_30.lock){
ConnectionHandler _cH;
_cH =cH ;

_cls_script_31 _cls_inst = _cls_script_31._get_cls_script_31_inst( _cH);
_cls_inst.cH = cH;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*evt_onConnected_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*evt_onConnected_cH*/);
}
}
before ( ConnectionHandler cH,String username) : (execution(* ConnectionHandler.user(..)) && target(cH) && args(username) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*)) && if (!username .startsWith ("anon"))) {

synchronized(_asp_script_30.lock){
ConnectionHandler _cH;
_cH =cH ;

_cls_script_31 _cls_inst = _cls_script_31._get_cls_script_31_inst( _cH);
_cls_inst.cH = cH;
_cls_inst.username = username;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 8/*evt_user_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 8/*evt_user_cH*/);
}
}
before ( ConnectionHandler cH) : (call(* ConnectionHandler.onDisconnected(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_30.lock){
ConnectionHandler _cH;
_cH =cH ;

_cls_script_31 _cls_inst = _cls_script_31._get_cls_script_31_inst( _cH);
_cls_inst.cH = cH;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 14/*evt_onDisconnected_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 14/*evt_onDisconnected_cH*/);
}
}
}