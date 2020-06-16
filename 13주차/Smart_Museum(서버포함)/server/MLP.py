import pandas as pd
import numpy as np
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense
from tensorflow.keras import optimizers

np.random.seed(5)

data = pd.read_csv('RSSI-Dataset.csv')
data['Cell'] = data['Cell'].replace(['A','B','C','D','E','F','G','H'],[0,1,2,3,4,5,6,7])
X_train = data[['AWifi', 'BWifi', 'CWifi']].values
y_train = data['Cell'].values
from tensorflow.keras.utils import to_categorical
y_train = to_categorical(y_train)
model=Sequential()
model.add(Dense(512, input_dim=3, activation='relu'))
model.add(Dense(512, activation='relu'))
model.add(Dense(8, activation='softmax'))
sgd=optimizers.SGD(lr=0.01)
model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['acc'])
hist=model.fit(X_train, y_train, batch_size=10, epochs=60)
class_names = ['A','B','C','D','E','F','G','H']
