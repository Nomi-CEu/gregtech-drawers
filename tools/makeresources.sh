#ported by 0xC4DE
olddir=$PWD
jar=ResourceCreator/target/ResourceCreator-1.0-jar-with-dependencies.jar

if ! [[ "$1" =~ "[[:digit:]]" ]] && ! [[ -z "$2" ]]; then
    modid=$1
    name=$2
fi

if [ -z "$modid" ] || [ -z "$name" ]; then
    printf "Invalid Usage\nUsage: ./makeresources.sh MODID NAME\nWhere NAME equals base_NAME.png and trim_NAME.png and MODID in MODID/base/ directory.\n"
    exit 1
fi

extra_params="${@:3}"
templates_dir="ResourceCreator/templates"
out_dir="../src/main/resources/assets/gregtechdrawers"
options="-i -iq -nrl"
textures="$out/textures/blocks"

echo modid=$modid
echo name="$name"
echo extra_args="$extra_params"

read -r -p "Correct arguments? [y/N]" response
case "$response" in 
    [yY])
        java -jar $jar -templates $templates_dir -out $out_dir -modid "$modid" -base "$textures/$modid/base/base_$name.png" -trim "$textures/$modid/base/trim_$name.png" -face "$textures/$modid/base/face_$name.png" $options $extra_params
        ;;
        *)
    exit 0 
esac
