cd ../

d1=dependencies/aspectjrt-1.8.13.jar
d2=dependencies/junit-4.12.jar
d3=dependencies/hamcrest-all-1.3.jar
d4=target/Testing-1.0-jar-with-dependencies.jar
d5=../TRE_Library/target/TRE_Library-1.0-jar-with-dependencies.jar

resDir="resources"
larvaDir="$resDir/precompiledScripts/generatedLarvaFiles"
classDir="$resDir/testClasses"
toolsDir="$resDir/commonSrc"
targetDir="target2/"

declare -a larvaFolders=("Larva_RE_Basic" "Larva_RE_Operators_GoodBeh" "Larva_RE_Operators_BadBeh" "Larva_RE_Mix" "Larva_RE_NonDet" "Larva_RE_Params_1" "Larva_RE_Params_2"
 "Larva_TA_Basic" "Larva_TA_Operators_GoodBeh" "Larva_TA_Operators_BadBeh" "Larva_TA_Mix" "Larva_TA_NonDet_1" "Larva_TA_NonDet_2" "Larva_TA_Params_1" "Larva_TA_Params_2" "Larva_TA_Params_3")
declare -a testClasses=("RE_basic" "RE_operators_g" "RE_operators_b" "RE_mix" "RE_nondet" "RE_params_1" "RE_params_2"
 "TA_basic" "TA_operators_g" "TA_operators_b" "TA_mix" "TA_nondet_1" "TA_nondet_2" "TA_params_1" "TA_params_2" "TA_params_3")

arrLenMinusOne=$(expr ${#larvaFolders[@]} - 1)
for i in `seq 0 $arrLenMinusOne`; do
    rm -r $targetDir
    echo "Compiling ${testClasses[$i]} test..."
    ajc -Xmx2g -1.8 -cp "$d1;$d2;$d4;$d5" -sourceroots "$larvaDir/${larvaFolders[$i]}/;$toolsDir/" $classDir/${testClasses[$i]}.java -d $targetDir -inpath $d4
    echo "Running ${testClasses[$i]} test..."
    aj5 -cp "$targetDir;$d2;$d3;$d4;$d5" org.junit.runner.JUnitCore "transactionsystem.${testClasses[$i]}"
    echo "Done!"
    #read -p "Press any key to run the next test... " -n1 -s
    echo
done

rm -r $targetDir
