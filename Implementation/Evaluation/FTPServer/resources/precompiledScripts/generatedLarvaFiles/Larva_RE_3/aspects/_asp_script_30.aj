package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.RE.RegExpHolder;
import RE.lib.basic.Symbol;
import com.guichaguri.minimalftp.handler.ConnectionHandler;
import com.guichaguri.minimalftp.FTPConnection;

import larva.*;
public aspect _asp_script_30 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script_30.initialize();
}
}
before ( RegExpHolder r) : (call(* RegExpHolder.triggerTimeout_hasEmptyString(..)) && target(r) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_30.lock){
int id;
id =r .getId ();

_cls_script_30 _cls_inst = _cls_script_30._get_cls_script_30_inst();
_cls_inst.r = r;
_cls_script_30.id = id;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*timeout_hasE*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*timeout_hasE*/);
}
}
before ( RegExpHolder r) : (call(* RegExpHolder.triggerTimeout_isEmpty(..)) && target(r) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_30.lock){
int id;
id =r .getId ();

_cls_script_30 _cls_inst = _cls_script_30._get_cls_script_30_inst();
_cls_inst.r = r;
_cls_script_30.id = id;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*timeout_isE*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*timeout_isE*/);
}
}
}