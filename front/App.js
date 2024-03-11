import {
  GluestackUIProvider,
  Text,
  Button,
  ButtonText,
  Image,
  View,
} from '@gluestack-ui/themed';
import {config} from '@gluestack-ui/config';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import SignupPage from './SignupPage.js';
import { NavigationContainer } from '@react-navigation/native';

const Stack = createNativeStackNavigator();

function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="StartPage">
        <Stack.Screen 
          name="StartPage" 
          component={ StartPage }
          options={{ headerShown: false }}
          />
        <Stack.Screen name="SignupPage" component={SignupPage}/>
      </Stack.Navigator>
    </NavigationContainer>
  );
}


// HomeScreen 컴포넌트
function StartPage({ navigation }) {
  return (
    <GluestackUIProvider config={config}>
      <View style={{ flex: 1, alignItems: 'center', marginTop: 150}}>
        <View>
          <Image source={require('./image/appname.png')} style={{ width: 200, height: 200, marginBottom: 70}}></Image>
        </View>
        <View style={{ alignItems: 'center' }}>
          <Text style={{ fontWeight: 'bold', marginBottom: 20}}>모두가 선택한 1위 식단관리 앱 D101</Text>
          <Text style={{ fontSize: 30, fontWeight: 'bold', marginBottom: 10}}>AI 기술 도입</Text>
          <Text style={{ fontSize: 30, fontWeight: 'bold' }}>식단관리 서비스</Text>
        </View>
        <View>
          <Button onPress={() => navigation.navigate('SignupPage')}>
            <ButtonText>시작하기</ButtonText>
          </Button>
        </View>
      </View>
    </GluestackUIProvider>
  );
}

export default App;