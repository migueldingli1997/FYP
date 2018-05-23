package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.RE.RegExpHolder;
import RE.lib.basic.Symbol;
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
_cH =cH ;

_cls_script_21 _cls_inst = _cls_script_21._get_cls_script_21_inst( _cH);
_cls_inst.cH = cH;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 6/*evt_stat_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 6/*evt_stat_cH*/);
}
}
before ( ConnectionHandler cH) : (execution(* ConnectionHandler.port(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){
ConnectionHandler _cH;
_cH =cH ;

_cls_script_21 _cls_inst = _cls_script_21._get_cls_script_21_inst( _cH);
_cls_inst.cH = cH;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 12/*evt_port_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 12/*evt_port_cH*/);
}
}
before ( ConnectionHandler cH) : (execution(* ConnectionHandler.mode(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){
ConnectionHandler _cH;
_cH =cH ;

_cls_script_21 _cls_inst = _cls_script_21._get_cls_script_21_inst( _cH);
_cls_inst.cH = cH;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 8/*evt_mode_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 8/*evt_mode_cH*/);
}
}
before ( ConnectionHandler cH) : (execution(* ConnectionHandler.syst(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){
ConnectionHandler _cH;
_cH =cH ;

_cls_script_21 _cls_inst = _cls_script_21._get_cls_script_21_inst( _cH);
_cls_inst.cH = cH;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*evt_syst_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*evt_syst_cH*/);
}
}
before ( ConnectionHandler cH) : (execution(* ConnectionHandler.pasv(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){
ConnectionHandler _cH;
_cH =cH ;

_cls_script_21 _cls_inst = _cls_script_21._get_cls_script_21_inst( _cH);
_cls_inst.cH = cH;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 10/*evt_pasv_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 10/*evt_pasv_cH*/);
}
}
after ( ConnectionHandler cH) returning (boolean authenticated) : (call(* ConnectionHandler.authenticate(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){
ConnectionHandler _cH;
_cH =cH ;

_cls_script_21 _cls_inst = _cls_script_21._get_cls_script_21_inst( _cH);
_cls_inst.authenticated = authenticated;
_cls_inst.cH = cH;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 14/*evt_authenticate_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 14/*evt_authenticate_cH*/);
}
}
}