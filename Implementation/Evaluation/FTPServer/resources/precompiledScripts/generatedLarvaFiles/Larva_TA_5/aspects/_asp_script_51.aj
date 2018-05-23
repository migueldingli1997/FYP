package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;
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
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*evt_cds_cH*/, 2/*evt_anySymbol_cH*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*evt_cds_cH*/, 2/*evt_anySymbol_cH*/);
}
}
before ( Object outcomeObj,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_script_51.channel_TAsToMain))) && args(outcomeObj)) {

synchronized(_asp_script_50.lock){
ConnectionHandler _cH;
String state;
final Outcome outcome =(Outcome )outcomeObj ;
state =outcome .getState ();
_cH =(ConnectionHandler )outcome .getVar (0 );

_cls_script_51 _cls_inst = _cls_script_51._get_cls_script_51_inst( _cH);
_cls_inst.outcomeObj = outcomeObj;
_cls_script_51.state = state;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 3/*evt_channelTAsToMain*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 3/*evt_channelTAsToMain*/);
}
}
}