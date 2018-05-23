package larva;


import java.util.*;
import larvaTools.LarvaController;
import larvaTools.RE.RegExpHolder;
import RE.lib.basic.Symbol;
import com.guichaguri.minimalftp.handler.ConnectionHandler;
import com.guichaguri.minimalftp.FTPConnection;

import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_script_30 implements _callable{

public static PrintWriter pw; 
public static _cls_script_30 root;

public static LinkedHashMap<_cls_script_30,_cls_script_30> _cls_script_30_instances = new LinkedHashMap<_cls_script_30,_cls_script_30>();
static{
try{
pw = new PrintWriter("larvaRuntimeOutput//output_script_3.txt");

root = new _cls_script_30();
_cls_script_30_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_script_30 parent; //to remain null - this class does not have a parent!
public static RegExpHolder r;
public static int id;
int no_automata = 0;
 public int exprIndex =RegExpHolder .parseAndStore ("typedef cH: com.guichaguri.minimalftp.handler.ConnectionHandler; [n] onConnected(cH).(auth(cH) & ~user(cH) & ~pass(cH)).( cds(cH).cds(cH).cds(cH).cds(cH).cds(cH).cds(cH) + ?*.(~onDisconnected(cH))[PT1H,inf] );");
 public Symbol Sym_onConnected_cH =new Symbol ("onConnected",Arrays .asList ("cH"));
 public Symbol Sym_auth_cH =new Symbol ("auth",Arrays .asList ("cH"));
 public Symbol Sym_user_cH =new Symbol ("user",Arrays .asList ("cH"));
 public Symbol Sym_pass_cH =new Symbol ("pass",Arrays .asList ("cH"));
 public Symbol Sym_cds_cH =new Symbol ("cds",Arrays .asList ("cH"));
 public Symbol Sym_onDisconnected_cH =new Symbol ("onDisconnected",Arrays .asList ("cH"));

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_script_30() {
}

public void initialisation() {
}

public static _cls_script_30 _get_cls_script_30_inst() { synchronized(_cls_script_30_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_script_30))
{return true;}
else
{return false;}
}

public int hashCode() {
return 1;
}

public void _call(String _info, int... _event){
synchronized(_cls_script_30_instances){
}
}

public void _call_all_filtered(String _info, int... _event){

_cls_script_31[] a1 = new _cls_script_31[1];
synchronized(_cls_script_31._cls_script_31_instances){
a1 = _cls_script_31._cls_script_31_instances.keySet().toArray(a1);}
for (_cls_script_31 _inst : a1)
if (_inst != null){
_inst._call(_info, _event); 
_inst._call_all_filtered(_info, _event);
}
}

public static void _call_all(String _info, int... _event){

_cls_script_30[] a = new _cls_script_30[1];
synchronized(_cls_script_30_instances){
a = _cls_script_30_instances.keySet().toArray(a);}
for (_cls_script_30 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_script_30_instances){
_cls_script_30_instances.remove(this);}
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