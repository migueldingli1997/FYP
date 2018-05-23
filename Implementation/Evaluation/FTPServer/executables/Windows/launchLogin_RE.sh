echo "Please enter the FTP server's port (e.g. 2021):"
read port

echo "Please enter the property number (Valid: 2/4):"
read prop

if [ $prop -eq 2 ] || [ $prop -eq 4 ]
then
	d1="dependencies/aspectjrt-1.8.13.jar"
	d2="target/classes/"
	d3="target/test-classes/"
	d4="../../TRE_Library/target/TRE_Library-1.0-jar-with-dependencies.jar"

	resDir="resources"
	larvaDir="$resDir/precompiledScripts/generatedLarvaFiles"
	toolsDir="$resDir/commonSrc"
	targetDir="target2/"

	declare -a larvaFolders=("Larva_RE_$prop")
	i=0

	cd ../../
	rm -r $targetDir
	echo "Compiling ${larvaFolders[$i]} experiment..."
	ajc -Xmx2g -1.8 -cp "$d1;$d2;$d3;$d4" -sourceroots "$larvaDir/${larvaFolders[$i]}/;$toolsDir/" -d $targetDir -inpath "$d2"
	echo "Running ${larvaFolders[$i]} experiment..."
	aj5 -Xverify:none -cp "$targetDir;$d2;$d3;$d4" "com.guichaguri.minimalftp.LoginServerRunner" $port
	echo "Done!"
else
	echo "Invalid scenario."
fi
