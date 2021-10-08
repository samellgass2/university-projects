from flask import Flask, request, url_for, session, redirect
import time
import spotipy
import pandas as pd
from spotipy.oauth2 import SpotifyOAuth

app = Flask(__name__)
app.secret_key = "VERYSECRET3892093029543042"
app.config['SESSION_COOKIE_NAME'] = 'User Cookie'
TOKEN_INFO = "Token here"


def create_oauth():
    # TO DO: MAKE THE CLIENT SECRET PRIVATE AND PASS IN VIA SOME PARSING OF A GIT IGNORED FILE
    """
    A function to create a spotify authorization from my client-side content
    :return: a SpotifyOAuth object, initialized for this application and permissions
    """
    return SpotifyOAuth(
        client_id='2b90b67f37914c32ad094916156f44aa',
        client_secret='c50524d981934e4c9e1dd88bcc2efacf',
        scope='playlist-read-private playlist-modify-private playlist-modify-public',
        redirect_uri=url_for('redirectPage', _external=True)
    )


def get_token():
    """
    Retrieves the token currently stored in the user's cache, or generates a new one if needed
    :return: token_info for use in authorization
    """
    token_info = session.get(TOKEN_INFO, None)
    if not token_info:
        return url_for("authorize", _external=False)
    now = int(time.time())

    expired = token_info['expires_at'] >= now
    if expired:
        oauth = create_oauth()
        token_info = oauth.refresh_access_token(token_info['refresh_token'])
    return token_info


@app.route('/')
def authorize():
    """
    Begins the authorization pipeline
    :return: a redirect to the authorization link
    """
    oauth = create_oauth()
    oauth_url = oauth.get_authorize_url()
    return redirect(oauth_url)


@app.route('/redirect')
def redirectPage():
    """
    After login, or if refreshed, store token info once processed
    :return: a redirect to the main function
    """
    oauth = create_oauth()
    session.clear()
    code = request.args.get('code')
    token_info = oauth.get_access_token(code)
    session[TOKEN_INFO] = token_info
    return redirect(url_for('processuserdata', _external=True))


@app.route('/processuserdata')
def processuserdata():
    """
    MAIN FUNCTION - DELEGATES WORK TO MODULES 2 AND 3
    :return: not yet clear *** fix ***
    """
    token_info = get_token()
    access = spotipy.Spotify(auth=token_info['access_token'])
    allplaylists = access.current_user_playlists(limit=10, offset=0)['items']
    USERID = access.me()['id']
    allpackages = []
    for playlist in allplaylists:
        if playlist['owner']['id']==USERID:
            pid = playlist['id']
            packagedplaylist = parse_playlist(pid, access)
            allpackages.append(packagedplaylist)
        # if it's NOT THE USER'S PLAYLIST, do nothing.

        # use playlist ID as the key and store all song ID's in tuples = packages

    # Once finished, pass this along to playlistparser to transform song data into arrays of pertinent data

    # Here the meat of the program will be run, i.e. Module 2, by passing this

    return 'Successfully processed ' + str(len(allpackages)) + ' playlists, with ' + str(sum([p[1].shape[0] for p in allpackages])) + 'total songs'


# TO DO - BUILD OUT FUNCTIONALITY FOR 100+ VIA FOR LOOP STYLE REQUESTS
def parse_playlist(pid, access, numsongs=100):
    """
    Takes in a playlistid and permissions and processes a playlist's songs into a dataframe for processing
    :param pid: a spotify playlistid (string)
    :param access: a spotipy.Spotify object with authorization access
    :param numsongs: the number of songs to consider from the playlist
    :return: a tuple of (pid, pd.DataFrame of track content)
    """
    trackframe = []
    thetracks = access.playlist_tracks(playlist_id=pid, limit=numsongs, offset=0)
    trackuris = [track['track']['uri'] for track in thetracks['items']]
    trackframe = pd.DataFrame(access.audio_features(trackuris))
    trackframe.to_csv('PLAYLIST DATA FOR PID='+pid)
    return (pid, trackframe)

