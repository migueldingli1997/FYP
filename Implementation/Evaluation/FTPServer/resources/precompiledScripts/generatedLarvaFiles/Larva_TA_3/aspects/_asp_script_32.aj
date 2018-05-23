package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;
import com.guichaguri.minimalftp.handler.ConnectionHandler;
import com.guichaguri.minimalftp.FTPConnection;

import larva.*;
public aspect _asp_script_32 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script_32.initialize();
}
}
before ( Object ssObj,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_script_31.channel_stateSkip))) && args(ssObj)) {

synchronized(_asp_script_30.lock){
AutomatonInstance ta;
ConnectionHandler _cH;
StateSkip ss;
ss =(StateSkip )ssObj ;
ta =ss .getInstance ();
_cH =(ConnectionHandler )ss .getVar (0 );

_cls_script_32 _cls_inst = _cls_script_32._get_cls_script_32_inst( ta,_cH);
_cls_inst.ssObj = ssObj;
_cls_script_32.ss = ss;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 15/*evt_channelStateSkip*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 15/*evt_channelStateSkip*/);
}
}
}