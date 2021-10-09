from sklearn.linear_model import LogisticRegression
import pandas as pd
import numpy as np


class DataPipeline:

    def __init__(self, listoflists):
        """
        Initialize the playlist processing pipeline and train the model on the User's playlists
        :param listoflists: a list of lists [pid, pd.DataFrame(audio_features)]
        """
        self.model = None
        self.data = [tup[1] for tup in listoflists]
        self.pids = [tup[0] for tup in listoflists]
        self.process()
        self.X = None
        self.Y = None

    def process(self):
        """
        Process the list of dataframes into a model trained on user playlist data
        :return: None
        """
        allplaylists = []
        i = 0
        for playlist in self.data:
            if playlist.shape[0] > 0:
                playlist['Playlist'] = i
                allplaylists.append(playlist)
            i+=1

        # Now we concatenate all of the playlists together to then process them
        # From this point on, SELF.DATA REFERS TO A SINGLE DATAFRAME OF ALL PLAYLIST DATA
        self.data = pd.concat(allplaylists)
        # From this point on, SELF.X, and SELF.Y correspond to training data for the model
        self.X, self.Y = self.dataprocess()
        self.model = LogisticRegression(C=2, multi_class='multinomial', max_iter=500)
        self.model.fit(self.X, self.Y)

    def dataprocess(self):
        """
        A subroutine of process to clean the audio_feature data
        :return: X, y as the data matrix and target matrix for some model
        """
        y = self.data['Playlist']
        # Remove non-predictive variables
        X = self.data.drop(columns=['type',
                                    'id', 'uri', 'track_href',
                                    'analysis_url', 'time_signature',
                                    'key', 'Playlist', 'mode'])
        # Standardize data
        X = (X - X.mean()) / X.std()
        return X, y

    def process_track(self, rawtrack):
        """
        Turn "track
        :param rawtrack:
        :return:
        """
        rawtrack.pop('type')
        rawtrack.pop('id')
        rawtrack.pop('uri')
        rawtrack.pop('track_href')
        rawtrack.pop('analysis_url')
        rawtrack.pop('time_signature')
        rawtrack.pop('key')
        rawtrack.pop('mode')
        return np.array(list(rawtrack.values())).reshape(1, -1)

    def predict(self, rawtrack, num_results):
        """
        Given a dictionary of audio_features for a track, processes the track, performs classification, and returns the num_results best playlist ids
        :param rawtrack: A dictionary of audio_features for a track
        :param num_results: Integer, number of playlists to return
        :return:
        """
        trackarr = self.process_track(rawtrack)
        # Because it expects multiple, single classification requires an index
        results = self.model.predict_proba(trackarr)[0]
        idsofinterest = []
        for i in np.arange(num_results):
            ind = np.argmax(results)
            idsofinterest.append(self.pids[ind])
            # Remove the maximum from the pool, then continue
            results[ind] = -1

        return idsofinterest



        


