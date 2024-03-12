import React from 'react';
import {View, StyleSheet, Image, TouchableOpacity} from 'react-native';
import {Button} from '@ui-kitten/components';

function StartButton({onPress}) {
  return (
    <TouchableOpacity
      onPress={onPress}
      style={{flex: 1, alignItems: 'center', marginTop: 50}}>
      <Image source={require('../images/startPage/Startbutton.png')}></Image>
    </TouchableOpacity>
  );
}

export default StartButton;
