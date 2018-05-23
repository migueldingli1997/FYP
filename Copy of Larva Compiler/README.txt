If the automatically-generated 'generatedScripts/' resulting from...

    - The evaluation script generator   ; OR
    - The test cases script generator   ; OR
    - The examples script generator

...is placed in this directory, the bash script...

    - compileEvaluationScripts.sh       ; OR
    - compileTestScripts.sh             ; OR
    - compileExampleScripts.sh

...can be executed so that all of the included scripts are compiled automatically.

Otherwise, the compileAnyScript.sh bash script can be used. This asks for the script
name and output directory, after which it compiles the script and places the resultant
folders (aspects/ and larva/) in the specified output directory. It is not recommended
to use 'out/' as the output directory, given that it holds the compiler's compiled code.