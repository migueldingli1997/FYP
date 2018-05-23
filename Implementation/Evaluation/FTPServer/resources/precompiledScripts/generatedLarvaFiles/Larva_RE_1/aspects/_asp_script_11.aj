package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.RE.RegExpHolder;
import RE.lib.basic.Symbol;
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
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*evt_process_ftpC*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*evt_process_ftpC*/);
}
}
before ( FTPConnection ftpC) : (call(* FTPConnection.sendResponse(..)) && target(ftpC) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_10.lock){
FTPConnection _ftpC;
_ftpC =ftpC ;

_cls_script_11 _cls_inst = _cls_script_11._get_cls_script_11_inst( _ftpC);
_cls_inst.ftpC = ftpC;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*evt_sendResponse_ftpC*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*evt_sendResponse_ftpC*/);
}
}
}