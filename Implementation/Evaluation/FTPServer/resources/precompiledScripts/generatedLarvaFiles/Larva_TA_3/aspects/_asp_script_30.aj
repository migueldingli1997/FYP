package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;
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
}