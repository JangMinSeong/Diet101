import React from 'react';
import {View, StyleSheet} from 'react-native';
import {Button} from '@ui-kitten/components';

function StartButton({onPress}) {
  return (
    <View style={styles.container}>
      <Button style={styles.button} onPress={onPress}>
        시작하기
      </Button>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    flexDirection: 'row',
    flexWrap: 'wrap',
  },
  button: {
    width: '80%',
    backgroundColor: 'black',
    marginTop: 60,
  },
});

export default StartButton;
