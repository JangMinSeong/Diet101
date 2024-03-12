import React from 'react';
import {View, StyleSheet, Image} from 'react-native';
import {Text} from '@ui-kitten/components';
import SignaturePicture from '../components/SignaturePicture';

function SignupPage() {
  return (
    <View style={{flex: 1}}>
      <SignaturePicture />
    </View>
  );
}

export default SignupPage;
