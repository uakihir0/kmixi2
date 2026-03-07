#!/usr/bin/env bash
cd "$(dirname "$0")" || exit
BASE_PATH=$(pwd)
BUILD_PATH=../all/build

# Make Repository
cd "$BASE_PATH" || exit
mkdir -p $BUILD_PATH/cocoapods/repository/debug
mkdir -p $BUILD_PATH/cocoapods/repository/release

# Copy Podspec
cd "$BASE_PATH" || exit
cd $BUILD_PATH/cocoapods/publish/debug || exit
cp kmixi2.podspec ../../repository/kmixi2-debug.podspec
cd ../../repository/ || exit
sed -i -e "s|'kmixi2'|'kmixi2-debug'|g" kmixi2-debug.podspec
sed -i -e "s|'kmixi2.xcframework'|'debug/kmixi2.xcframework'|g" kmixi2-debug.podspec
rm *.podspec-e
cd "$BASE_PATH" || exit
cd $BUILD_PATH/cocoapods/publish/release || exit
cp kmixi2.podspec ../../repository/kmixi2-release.podspec
cd ../../repository/ || exit
sed -i -e "s|'kmixi2'|'kmixi2-release'|g" kmixi2-release.podspec
sed -i -e "s|'kmixi2.xcframework'|'release/kmixi2.xcframework'|g" kmixi2-release.podspec
rm *.podspec-e

# Copy Framework
cd "$BASE_PATH" || exit
cd $BUILD_PATH/cocoapods/publish/debug || exit
cp -r kmixi2.xcframework ../../repository/debug/kmixi2.xcframework
cd "$BASE_PATH" || exit
cd $BUILD_PATH/cocoapods/publish/release || exit
cp -r kmixi2.xcframework ../../repository/release/kmixi2.xcframework

# Copy README
cd "$BASE_PATH" || exit
cd ../ || exit
cp ./LICENSE ./all/build/cocoapods/repository/LICENSE
