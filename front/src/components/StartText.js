import React from 'react';
import {View, StyleSheet} from 'react-native';
import {Text} from '@ui-kitten/components';

function StartText() {
  return (
    <View style={{flex: 1}}>
      <View style={styles.container}>
        <Text category="h6" style={{marginBottom: 30}}>
          모두가 선택한 1위 식단관리 앱 D101
        </Text>
      </View>
      <View style={styles.container}>
        <Text category="h4">AI 기술 도입</Text>
      </View>
      <View style={styles.container}>
        <Text category="h4">식단 관리 서비스</Text>
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

export default StartText;
