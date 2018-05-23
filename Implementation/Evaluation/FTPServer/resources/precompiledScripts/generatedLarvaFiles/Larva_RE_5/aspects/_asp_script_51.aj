package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.RE.RegExpHolder;
import RE.lib.basic.Symbol;
import com.guichaguri.minimalftp.handler.ConnectionHandler;

import larva.*;
public aspect _asp_script_51 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script_51.initialize();
}
}
before ( ConnectionHandler cH) : (call(* ConnectionHandler.createDataSocket(..)) && target(cH) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_50.lock){
ConnectionHandler _cH;
_cH =cH ;

_cls_script_51 _cls_inst = _cls_script_51._get_cls_script_51_inst( _cH);
_cls_inst.cH = cH;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*evt_cds_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*evt_cds_cH*/);
}
}
}