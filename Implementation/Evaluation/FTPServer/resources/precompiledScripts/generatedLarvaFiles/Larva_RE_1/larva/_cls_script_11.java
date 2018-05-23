package larva;


import java.util.*;
import larvaTools.LarvaController;
import larvaTools.RE.RegExpHolder;
import RE.lib.basic.Symbol;
import com.guichaguri.minimalftp.FTPConnection;

import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_script_11 implements _callable{

public static LinkedHashMap<_cls_script_11,_cls_script_11> _cls_script_11_instances = new LinkedHashMap<_cls_script_11,_cls_script_11>();

_cls_script_10 parent;
public static FTPConnection ftpC;
public FTPConnection _ftpC;
int no_automata = 1;
 public RegExpHolder reh =null ;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_script_11( FTPConnection _ftpC) {
parent = _cls_script_10._get_cls_script_10_inst();
this._ftpC = _ftpC;
}

public void initialisation() {

reh =new RegExpHolder (parent.exprIndex );

}

public static _cls_script_11 _get_cls_script_11_inst( FTPConnection _ftpC) { synchronized(_cls_script_11_instances){
_cls_script_11 _inst = new _cls_script_11( _ftpC);
if (_cls_script_11_instances.containsKey(_inst))
{
_cls_script_11 tmp = _cls_script_11_instances.get(_inst);
 return _cls_script_11_instances.get(_inst);
}
else
{
 _cls_script_11_instances.put(_inst,_inst);
 _inst.initialisation();
 return _inst;
}
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_script_11)
 && (_ftpC == null || _ftpC.equals(((_cls_script_11)o)._ftpC))
 && (parent == null || parent.equals(((_cls_script_11)o).parent)))
{return true;}
else
{return false;}
}

public int hashCode() {
return (_ftpC==null?1:_ftpC.hashCode()) *(parent==null?1:parent.hashCode()) *1;
}

public void _call(String _info, int... _event){
synchronized(_cls_script_11_instances){
_performLogic_property(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_script_11[] a = new _cls_script_11[1];
synchronized(_cls_script_11_instances){
a = _cls_script_11_instances.keySet().toArray(a);}
for (_cls_script_11 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_script_11_instances){
_cls_script_11_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_property = 1;

public void _performLogic_property(String _info, int... _event) {

if (0==1){}
else if (_state_id_property==1){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*timeout_isE*/)) && (reh .getId ()==parent.id )){
		reh .stop ();

		_state_id_property = 0;//moving to state bad
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,2/*evt_process_ftpC*/)) && (reh .toDerivative (parent.Sym_process_ftpC ).isEmpty ())){
		reh .stop ();

		_state_id_property = 0;//moving to state bad
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,4/*evt_sendResponse_ftpC*/)) && (reh .toDerivative (parent.Sym_sendResponse_ftpC ).isEmpty ())){
		reh .stop ();

		_state_id_property = 0;//moving to state bad
		_goto_property(_info);
		}
}
}

public void _goto_property(String _info){
 String state_format = _string_property(_state_id_property, 1);
 if (state_format.startsWith("!!!SYSTEM REACHED BAD STATE!!!")) {
_cls_script_10.pw.println("[property]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + state_format);
_cls_script_10.pw.flush();
}
}

public String _string_property(int _state_id, int _mode){
switch(_state_id){
case 0: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionscript_1().toString()+" ";
case 1: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}