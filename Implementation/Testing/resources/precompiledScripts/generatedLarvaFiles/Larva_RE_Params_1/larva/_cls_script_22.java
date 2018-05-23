package larva;


import java.util.*;
import larvaTools.LarvaController;
import larvaTools.RE.RegExpHolder;
import RE.lib.basic.Symbol;
import transactionsystem.UserInfo;
import transactionsystem.UserAccount;

import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_script_22 implements _callable{

public static LinkedHashMap<_cls_script_22,_cls_script_22> _cls_script_22_instances = new LinkedHashMap<_cls_script_22,_cls_script_22>();

_cls_script_21 parent;
public static UserAccount ua;
public UserAccount _ua;
int no_automata = 1;
 public RegExpHolder reh =null ;
 public int currentState =0 ;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_script_22( UserAccount _ua,UserInfo _ui) {
parent = _cls_script_21._get_cls_script_21_inst( _ui);
this._ua = _ua;
}

public void initialisation() {

currentState =0 ;
reh =new RegExpHolder (parent.parent.exprIndex );

}

public static _cls_script_22 _get_cls_script_22_inst( UserAccount _ua,UserInfo _ui) { synchronized(_cls_script_22_instances){
_cls_script_22 _inst = new _cls_script_22( _ua,_ui);
if (_cls_script_22_instances.containsKey(_inst))
{
_cls_script_22 tmp = _cls_script_22_instances.get(_inst);
 return _cls_script_22_instances.get(_inst);
}
else
{
 _cls_script_22_instances.put(_inst,_inst);
 _inst.initialisation();
 return _inst;
}
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_script_22)
 && (_ua == null || _ua.equals(((_cls_script_22)o)._ua))
 && (parent == null || parent.equals(((_cls_script_22)o).parent)))
{return true;}
else
{return false;}
}

public int hashCode() {
return (_ua==null?1:_ua.hashCode()) *(parent==null?1:parent.hashCode()) *1;
}

public void _call(String _info, int... _event){
synchronized(_cls_script_22_instances){
_performLogic_property(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_script_22[] a = new _cls_script_22[1];
synchronized(_cls_script_22_instances){
a = _cls_script_22_instances.keySet().toArray(a);}
for (_cls_script_22 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_script_22_instances){
_cls_script_22_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_property = 2;

public void _performLogic_property(String _info, int... _event) {

if (0==1){}
else if (_state_id_property==1){
		if (1==0){}
		else if ((_occurredEvent(_event,2/*evt_LarvaReset*/))){
		
		_state_id_property = 2;//moving to state start
currentState =0 ;
reh =new RegExpHolder (parent.parent.exprIndex );

		_goto_property(_info);
		}
}
else if (_state_id_property==0){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*evt_LarvaStop*/))){
		reh .stop ();

		_state_id_property = 1;//moving to state stopped
		_goto_property(_info);
		}
}
else if (_state_id_property==2){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*timeout_isE*/)) && (reh .getId ()==parent.parent.id )){
		reh .stop ();

		_state_id_property = 0;//moving to state bad
currentState =-1 ;

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,6/*evt_deposit_ui_ua*/)) && (reh .toDerivative (parent.parent.Sym_deposit_ui_ua ).isEmpty ())){
		reh .stop ();

		_state_id_property = 0;//moving to state bad
currentState =-1 ;

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,8/*evt_withdraw_ui_ua*/)) && (reh .toDerivative (parent.parent.Sym_withdraw_ui_ua ).isEmpty ())){
		reh .stop ();

		_state_id_property = 0;//moving to state bad
currentState =-1 ;

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,4/*evt_LarvaStop*/))){
		reh .stop ();

		_state_id_property = 1;//moving to state stopped
		_goto_property(_info);
		}
}
}

public void _goto_property(String _info){
 String state_format = _string_property(_state_id_property, 1);
 if (state_format.startsWith("!!!SYSTEM REACHED BAD STATE!!!")) {
_cls_script_20.pw.println("[property]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + state_format);
_cls_script_20.pw.flush();
}
}

public String _string_property(int _state_id, int _mode){
switch(_state_id){
case 1: if (_mode == 0) return "stopped"; else return "stopped";
case 0: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionscript_2().toString()+" ";
case 2: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}