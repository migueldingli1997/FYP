package larva;


import java.util.*;
import larvaTools.LarvaController;
import larvaTools.RE.RegExpHolder;
import RE.lib.basic.Symbol;
import com.guichaguri.minimalftp.handler.ConnectionHandler;

import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_script_50 implements _callable{

public static PrintWriter pw; 
public static _cls_script_50 root;

public static LinkedHashMap<_cls_script_50,_cls_script_50> _cls_script_50_instances = new LinkedHashMap<_cls_script_50,_cls_script_50>();
static{
try{
pw = new PrintWriter("larvaRuntimeOutput//output_script_5.txt");

root = new _cls_script_50();
_cls_script_50_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_script_50 parent; //to remain null - this class does not have a parent!
public static RegExpHolder r;
public static int id;
int no_automata = 0;
 public int exprIndex =RegExpHolder .parseAndStore ("typedef cH: com.guichaguri.minimalftp.handler.ConnectionHandler; [n] ?*.cds(cH).(cds(cH).cds(cH).cds(cH).cds(cH))[0,10];");
 public Symbol Sym_cds_cH =new Symbol ("cds",Arrays .asList ("cH"));

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_script_50() {
}

public void initialisation() {
}

public static _cls_script_50 _get_cls_script_50_inst() { synchronized(_cls_script_50_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_script_50))
{return true;}
else
{return false;}
}

public int hashCode() {
return 1;
}

public void _call(String _info, int... _event){
synchronized(_cls_script_50_instances){
}
}

public void _call_all_filtered(String _info, int... _event){

_cls_script_51[] a1 = new _cls_script_51[1];
synchronized(_cls_script_51._cls_script_51_instances){
a1 = _cls_script_51._cls_script_51_instances.keySet().toArray(a1);}
for (_cls_script_51 _inst : a1)
if (_inst != null){
_inst._call(_info, _event); 
_inst._call_all_filtered(_info, _event);
}
}

public static void _call_all(String _info, int... _event){

_cls_script_50[] a = new _cls_script_50[1];
synchronized(_cls_script_50_instances){
a = _cls_script_50_instances.keySet().toArray(a);}
for (_cls_script_50 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_script_50_instances){
_cls_script_50_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}


public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}