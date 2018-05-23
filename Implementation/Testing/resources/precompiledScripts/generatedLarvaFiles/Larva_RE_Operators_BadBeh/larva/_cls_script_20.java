package larva;


import java.util.*;
import larvaTools.LarvaController;
import larvaTools.RE.RegExpHolder;
import RE.lib.basic.Symbol;

import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_script_20 implements _callable{

public static PrintWriter pw; 
public static _cls_script_20 root;

public static LinkedHashMap<_cls_script_20,_cls_script_20> _cls_script_20_instances = new LinkedHashMap<_cls_script_20,_cls_script_20>();
static{
try{
pw = new PrintWriter("larvaRuntimeOutput//output_script_2.txt");

root = new _cls_script_20();
_cls_script_20_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_script_20 parent; //to remain null - this class does not have a parent!
public static LarvaController __1;
public static LarvaController __0;
public static RegExpHolder r;
public static int id;
int no_automata = 1;
 public int exprIndex =RegExpHolder .parseAndStore ("[n] ADMIN_initialise + ADMIN_reconcile; {ADMIN_createUser}");
 public Symbol Sym_ADMIN_initialise =new Symbol ("ADMIN_initialise");
 public Symbol Sym_ADMIN_reconcile =new Symbol ("ADMIN_reconcile");
 public Symbol Sym_ADMIN_createUser =new Symbol ("ADMIN_createUser");
 public RegExpHolder reh =null ;
 public int currentState =0 ;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_script_20() {
}

public void initialisation() {

currentState =0 ;
reh =new RegExpHolder (exprIndex );

}

public static _cls_script_20 _get_cls_script_20_inst() { synchronized(_cls_script_20_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_script_20))
{return true;}
else
{return false;}
}

public int hashCode() {
return 1;
}

public void _call(String _info, int... _event){
synchronized(_cls_script_20_instances){
_performLogic_property(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_script_20[] a = new _cls_script_20[1];
synchronized(_cls_script_20_instances){
a = _cls_script_20_instances.keySet().toArray(a);}
for (_cls_script_20 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_script_20_instances){
_cls_script_20_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_property = 3;

public void _performLogic_property(String _info, int... _event) {

if (0==1){}
else if (_state_id_property==2){
		if (1==0){}
		else if ((_occurredEvent(_event,10/*evt_LarvaReset*/))){
		
		_state_id_property = 3;//moving to state start
currentState =0 ;
reh =new RegExpHolder (exprIndex );

		_goto_property(_info);
		}
}
else if (_state_id_property==1){
		if (1==0){}
		else if ((_occurredEvent(_event,12/*evt_LarvaStop*/))){
		reh .stop ();

		_state_id_property = 2;//moving to state stopped
		_goto_property(_info);
		}
}
else if (_state_id_property==3){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*timeout_hasE*/)) && (reh .getId ()==id )){
		reh .stop ();

		_state_id_property = 1;//moving to state bad
currentState =-1 ;

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,6/*timeout_isE*/)) && (reh .getId ()==id )){
		reh .stop ();

		_state_id_property = 0;//moving to state good
currentState =1 ;

		_goto_property(_info);
           //_killThis(); //discard this automaton since an accepting state has been reached
		}
		else if ((_occurredEvent(_event,0/*evt_ADMIN_initialise*/)) && (reh .toDerivative (Sym_ADMIN_initialise ).hasEmptyString ())){
		reh .stop ();

		_state_id_property = 1;//moving to state bad
currentState =-1 ;

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,2/*evt_ADMIN_reconcile*/)) && (reh .toDerivative (Sym_ADMIN_reconcile ).hasEmptyString ())){
		reh .stop ();

		_state_id_property = 1;//moving to state bad
currentState =-1 ;

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,4/*evt_ADMIN_createUser*/)) && (reh .toDerivative (Sym_ADMIN_createUser ).hasEmptyString ())){
		reh .stop ();

		_state_id_property = 1;//moving to state bad
currentState =-1 ;

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,0/*evt_ADMIN_initialise*/)) && (reh .getRegExp ().isEmpty ())){
		reh .stop ();

		_state_id_property = 0;//moving to state good
currentState =1 ;

		_goto_property(_info);
           //_killThis(); //discard this automaton since an accepting state has been reached
		}
		else if ((_occurredEvent(_event,2/*evt_ADMIN_reconcile*/)) && (reh .getRegExp ().isEmpty ())){
		reh .stop ();

		_state_id_property = 0;//moving to state good
currentState =1 ;

		_goto_property(_info);
           //_killThis(); //discard this automaton since an accepting state has been reached
		}
		else if ((_occurredEvent(_event,4/*evt_ADMIN_createUser*/)) && (reh .getRegExp ().isEmpty ())){
		reh .stop ();

		_state_id_property = 0;//moving to state good
currentState =1 ;

		_goto_property(_info);
           //_killThis(); //discard this automaton since an accepting state has been reached
		}
		else if ((_occurredEvent(_event,12/*evt_LarvaStop*/))){
		reh .stop ();

		_state_id_property = 2;//moving to state stopped
		_goto_property(_info);
		}
}
else if (_state_id_property==0){
		if (1==0){}
		else if ((_occurredEvent(_event,12/*evt_LarvaStop*/))){
		reh .stop ();

		_state_id_property = 2;//moving to state stopped
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
case 2: if (_mode == 0) return "stopped"; else return "stopped";
case 1: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionscript_2().toString()+" ";
case 3: if (_mode == 0) return "start"; else return "start";
case 0: if (_mode == 0) return "good"; else return "(((SYSTEM REACHED AN ACCEPTED STATE)))  good";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}