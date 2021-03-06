package larva;


import java.util.*;
import larvaTools.LarvaController;
import larvaTools.RE.RegExpHolder;
import RE.lib.basic.Symbol;
import com.guichaguri.minimalftp.handler.ConnectionHandler;

import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_script_41 implements _callable{

public static LinkedHashMap<_cls_script_41,_cls_script_41> _cls_script_41_instances = new LinkedHashMap<_cls_script_41,_cls_script_41>();

_cls_script_40 parent;
public static boolean needs;
public static boolean authenticated;
public static ConnectionHandler cH;
public ConnectionHandler _cH;
int no_automata = 1;
 public RegExpHolder reh =null ;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_script_41( ConnectionHandler _cH) {
parent = _cls_script_40._get_cls_script_40_inst();
this._cH = _cH;
}

public void initialisation() {

reh =new RegExpHolder (parent.exprIndex );

}

public static _cls_script_41 _get_cls_script_41_inst( ConnectionHandler _cH) { synchronized(_cls_script_41_instances){
_cls_script_41 _inst = new _cls_script_41( _cH);
if (_cls_script_41_instances.containsKey(_inst))
{
_cls_script_41 tmp = _cls_script_41_instances.get(_inst);
 return _cls_script_41_instances.get(_inst);
}
else
{
 _cls_script_41_instances.put(_inst,_inst);
 _inst.initialisation();
 return _inst;
}
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_script_41)
 && (_cH == null || _cH.equals(((_cls_script_41)o)._cH))
 && (parent == null || parent.equals(((_cls_script_41)o).parent)))
{return true;}
else
{return false;}
}

public int hashCode() {
return (_cH==null?1:_cH.hashCode()) *(parent==null?1:parent.hashCode()) *1;
}

public void _call(String _info, int... _event){
synchronized(_cls_script_41_instances){
_performLogic_property(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_script_41[] a = new _cls_script_41[1];
synchronized(_cls_script_41_instances){
a = _cls_script_41_instances.keySet().toArray(a);}
for (_cls_script_41 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_script_41_instances){
_cls_script_41_instances.remove(this);}
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
		else if ((_occurredEvent(_event,2/*evt_onConnected_cH*/)) && (reh .toDerivative (parent.Sym_onConnected_cH ).isEmpty ())){
		reh .stop ();

		_state_id_property = 0;//moving to state bad
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,4/*evt_needUser_cH*/)) && (needs &&reh .toDerivative (parent.Sym_needUser_cH ).isEmpty ())){
		reh .stop ();

		_state_id_property = 0;//moving to state bad
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,6/*evt_user_cH*/)) && (reh .toDerivative (parent.Sym_user_cH ).isEmpty ())){
		reh .stop ();

		_state_id_property = 0;//moving to state bad
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,8/*evt_needPass_cH*/)) && (needs &&reh .toDerivative (parent.Sym_needPass_cH ).isEmpty ())){
		reh .stop ();

		_state_id_property = 0;//moving to state bad
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,10/*evt_pass_cH*/)) && (reh .toDerivative (parent.Sym_pass_cH ).isEmpty ())){
		reh .stop ();

		_state_id_property = 0;//moving to state bad
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,12/*evt_authenticate_cH*/)) && (authenticated &&reh .toDerivative (parent.Sym_authenticate_cH ).isEmpty ())){
		reh .stop ();

		_state_id_property = 0;//moving to state bad
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,14/*evt_createDataSocket_cH*/)) && (reh .toDerivative (parent.Sym_createDataSocket_cH ).isEmpty ())){
		reh .stop ();

		_state_id_property = 0;//moving to state bad
		_goto_property(_info);
		}
}
}

public void _goto_property(String _info){
 String state_format = _string_property(_state_id_property, 1);
 if (state_format.startsWith("!!!SYSTEM REACHED BAD STATE!!!")) {
_cls_script_40.pw.println("[property]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + state_format);
_cls_script_40.pw.flush();
}
}

public String _string_property(int _state_id, int _mode){
switch(_state_id){
case 0: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionscript_4().toString()+" ";
case 1: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}