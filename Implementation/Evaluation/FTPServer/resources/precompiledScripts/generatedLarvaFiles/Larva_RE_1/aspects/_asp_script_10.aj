package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.RE.RegExpHolder;
import RE.lib.basic.Symbol;
import com.guichaguri.minimalftp.FTPConnection;

import larva.*;
public aspect _asp_script_10 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script_10.initialize();
}
}
before ( RegExpHolder r) : (call(* RegExpHolder.triggerTimeout_isEmpty(..)) && target(r) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_10.lock){
int id;
id =r .getId ();

_cls_script_10 _cls_inst = _cls_script_10._get_cls_script_10_inst();
_cls_inst.r = r;
_cls_script_10.id = id;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*timeout_isE*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*timeout_isE*/);
}
}
}