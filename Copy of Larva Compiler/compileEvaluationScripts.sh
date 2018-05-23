declare -a folders=("RE" "TA")
count=5

rm -r generatedLarvaFiles
mkdir generatedLarvaFiles

arrLenMinusOne=$(expr ${#folders[@]} - 1)
for i in `seq 0 $arrLenMinusOne`; do
	if [ -d "generatedScripts/Scripts_${folders[$i]}/" ]; then
		for j in `seq 1 $count`; do
			if [ -f "generatedScripts/Scripts_${folders[$i]}/script_$j.lrv" ]; then
				cp generatedScripts/Scripts_${folders[$i]}/script_$j.lrv .
				java -cp out/ compiler.Compiler script_$j.lrv -o "larvaRuntimeOutput" -g "%GRAPHVIZ_DOT%"
				mv larvaRuntimeOutput generatedLarvaFiles/Larva_${folders[$i]}_$j
				rm *.lrv
			else
				echo "generatedScripts/Scripts_${folders[$i]}/script_$j.lrv script not found"
			fi
		done
	else
		echo "generatedScripts/Scripts_${folders[$i]}/ folder not found."
	fi
done