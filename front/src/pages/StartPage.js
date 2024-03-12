import React from 'react';
import {View} from 'react-native';
import AppIntroPicture from '../components/AppIntroPicture.js';
import StartText from '../components/StartText.js';
import StartButton from '../components/StartButton.js';

// navigation prop
function StartPage({navigation}) {
  return (
    <View style={{flex: 1}}>
      <AppIntroPicture />
      <View style={{flex: 1}}>
        <StartText />
        {/* StartButton에 navigation prop과 navigate 함수 전달 */}
        <StartButton onPress={() => navigation.navigate('Signup')} />
      </View>
    </View>
  );
}

export default StartPage;
