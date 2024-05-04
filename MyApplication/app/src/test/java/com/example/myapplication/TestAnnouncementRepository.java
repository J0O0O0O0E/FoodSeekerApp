package com.example.myapplication;
import com.example.myapplication.model.Announcement;
import com.example.myapplication.repository.AnnouncementRepository;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.QuerySnapshot;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import java.util.List;

public class TestAnnouncementRepository {
    private FirebaseFirestore mockFirestore;
    private AnnouncementRepository repository;

    @Before
    public void setUp() {
        mockFirestore = mock(FirebaseFirestore.class);
        Mockito.mockStatic(FirebaseFirestore.class).when(FirebaseFirestore::getInstance).thenReturn(mockFirestore);
        repository = new AnnouncementRepository();
    }

    @Test
    public void testLoadAnnouncements() {

        QuerySnapshot mockSnapshot = mock(QuerySnapshot.class);

        doAnswer(invocation -> {
            EventListener<QuerySnapshot> listener = invocation.getArgument(0);
            listener.onEvent(mockSnapshot, null);
            return null;
        }).when(mockFirestore.collection("Announcement")).addSnapshotListener(any());
        repository.loadAnnouncements();
        MutableLiveData<List<Announcement>> liveData = (MutableLiveData<List<Announcement>>) repository.getAnnouncements();
        assertNotNull(liveData.getValue());
        assertTrue(liveData.getValue().isEmpty());
    }
}
