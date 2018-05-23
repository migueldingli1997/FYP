package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;
import com.guichaguri.minimalftp.FTPConnection;

import larva.*;
public aspect _asp_script_11 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script_11.initialize();
}
}
before ( FTPConnection ftpC) : (call(* FTPConnection.process(..)) && target(ftpC) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_10.lock){
FTPConnection _ftpC;
_ftpC =ftpC ;

_cls_script_11 _cls_inst = _cls_script_11._get_cls_script_11_inst( _ftpC);
_cls_inst.ftpC = ftpC;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*evt_process_ftpC*/, 4/*evt_anySymbol_ftpC*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*evt_process_ftpC*/, 4/*evt_anySymbol_ftpC*/);
}
}
before ( Object outcomeObj,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_script_11.channel_TAsToMain))) && args(outcomeObj)) {

synchronized(_asp_script_10.lock){
FTPConnection _ftpC;
String state;
final Outcome outcome =(Outcome )outcomeObj ;
state =outcome .getState ();
_ftpC =(FTPConnection )outcome .getVar (0 );

_cls_script_11 _cls_inst = _cls_script_11._get_cls_script_11_inst( _ftpC);
_cls_inst.outcomeObj = outcomeObj;
_cls_script_11.state = state;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 5/*evt_channelTAsToMain*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 5/*evt_channelTAsToMain*/);
}
}
before ( FTPConnection ftpC) : (call(* FTPConnection.sendResponse(..)) && target(ftpC) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_10.lock){
FTPConnection _ftpC;
_ftpC =ftpC ;

_cls_script_11 _cls_inst = _cls_script_11._get_cls_script_11_inst( _ftpC);
_cls_inst.ftpC = ftpC;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*evt_sendResponse_ftpC*/, 4/*evt_anySymbol_ftpC*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*evt_sendResponse_ftpC*/, 4/*evt_anySymbol_ftpC*/);
}
}
}