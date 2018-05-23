package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.RE.RegExpHolder;
import RE.lib.basic.Symbol;
import com.guichaguri.minimalftp.handler.ConnectionHandler;

import larva.*;
public aspect _asp_script_40 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script_40.initialize();
}
}
before ( RegExpHolder r) : (call(* RegExpHolder.triggerTimeout_isEmpty(..)) && target(r) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_40.lock){
int id;
id =r .getId ();

_cls_script_40 _cls_inst = _cls_script_40._get_cls_script_40_inst();
_cls_inst.r = r;
_cls_script_40.id = id;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*timeout_isE*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*timeout_isE*/);
}
}
}