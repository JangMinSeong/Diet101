import React from 'react';
import {View, StyleSheet} from 'react-native';
import {Text} from '@ui-kitten/components';

function SignupPage() {
  return (
    <View style={{flex: 1}}>
      <View style={styles.container}>
        <Text category="h6" style={{marginBottom: 30}}>
          Singup
        </Text>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    justifyContent: 'center',
    alignItems: 'center',
  },
});

export default SignupPage;
