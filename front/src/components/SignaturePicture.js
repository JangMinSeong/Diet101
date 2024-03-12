import React from 'react';
import {View, StyleSheet, Image} from 'react-native';
import {Text} from '@ui-kitten/components';

function SignaturePicture() {
  return (
    <View style={{flex: 1, alignItems: 'center', marginTop: 80}}>
      <Image
        source={require('../images/signupPage/signature.png')}
        style={{width: 220, height: 220}}></Image>
    </View>
  );
}

export default SignaturePicture;
